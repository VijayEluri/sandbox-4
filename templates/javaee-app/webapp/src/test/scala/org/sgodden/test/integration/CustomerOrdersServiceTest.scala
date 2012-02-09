package org.sgodden.test.integration

import org.sgodden.tom.web.ListResponse
import org.testng.Assert
import org.sgodden.tom.services.customerorder.CustomerOrderListEntry
import java.util.Calendar
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.entity.StringEntity
import org.codehaus.jackson.map.{SerializationConfig, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.{HttpEntity, HttpResponse}
import java.io.{InputStreamReader, BufferedReader}
import org.testng.annotations.Test
import org.slf4j.LoggerFactory
import collection.mutable.Buffer

@Test(groups = Array("integration"))
class CustomerOrdersServiceTest {

  private def LOG = LoggerFactory.getLogger(classOf[CustomerOrdersServiceTest])

  private val baseUri = "http://localhost:8080/webapp/services/customerOrders"

  @Test(priority = 1)
  def shouldBeNoOrders: Unit = {
    Assert.assertEquals(listOrders.customerOrders.size, 0)
  }

  @Test(priority = 2)
  def testCreateOrder: Unit = {
    val order = new CustomerOrderListEntry {
      setBookingDate(Calendar.getInstance)
      setCustomerReference("cr001")
      setOrderNumber("ORD001")
    }

    val response = postOrder(order)
    printErrorsIfExist(response)
    Assert.assertTrue(response.success)

    val returned = response.customerOrders(0)
    Assert.assertNotNull(returned)
    Assert.assertEquals(1l, returned.getId)
  }

  @Test(priority = 2)
  def customerReferenceMustBeginWithCrOnCreate: Unit = {
    val order = new CustomerOrderListEntry {
      setBookingDate(Calendar.getInstance)
      setCustomerReference("CREF001")
      setOrderNumber("ORD001")
    }

    val response = postOrder(order)
    Assert.assertFalse(response.success)
    Assert.assertTrue(containsError(response.errors, "customerReference", "Customer reference must begin with 'cr'"))
  }
  
  private def printErrorsIfExist(response: ListResponse) {
    if (!response.success) println(response.errors(0).message)
  }

  private def postOrder(order: CustomerOrderListEntry): ListResponse = {
    val client = new DefaultHttpClient
    val post = new HttpPost(baseUri) {
      setEntity(new StringEntity(objectMapper.writeValueAsString(order)) {
        setContentType("application/json")
      })
    }
    toListOrdersResponse(client.execute(post))
  }
  
  private def toListOrdersResponse(response: HttpResponse): ListResponse = {
    objectMapper.reader(classOf[ListResponse]).readValue(getResponseString(response.getEntity))
  }

  private def listOrders: ListResponse = {
    val ordersString = getListOrdersResponse
    objectMapper.reader(classOf[ListResponse]).readValue(ordersString)
  }

  private def objectMapper: ObjectMapper = {
    new ObjectMapper {
      configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false)
      withModule(new DefaultScalaModule)
    }
  }

  private def getListOrdersResponse: String = {
    val client = new DefaultHttpClient
    val get = new HttpGet(baseUri)
    val response = client.execute(get)
    getResponseString(response.getEntity)
  }

  private def getResponseString(entity: HttpEntity): String = {
    if (entity == null) {
      return null
    }
    val reader = new BufferedReader(new InputStreamReader(entity.getContent))
    val ret = new StringBuilder
    var line: String = null
    while ((({
      line = reader.readLine; line
    })) != null) {
      ret.append(line)
    }
    ret.toString()
  }


  private def containsError(
                                 errors: Buffer[org.sgodden.tom.web.Error],
                                 path: String,
                                 message: String): Boolean = {
    errors.filter(error => path == error.path && message == error.message).size > 0
  }

}