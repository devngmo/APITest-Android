package tml.tools.apitest

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import tml.libs.cku.textutils.UltimateJsonParser

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TestJsonParser {
    @Test
    fun parseSimpleForm() {
        val text = "k1:v1\r\nK2:v2\r\nk3:v3"
        var parsed = UltimateJsonParser.parseRawText(text, lineBreak = "\r\n")
        Assert.assertNotNull(parsed)
        System.out.println(parsed)
        Assert.assertEquals("{\"k1\":\"v1\",\"K2\":\"v2\",\"k3\":\"v3\"}", parsed)


        parsed = UltimateJsonParser.parseRawText(text, true, lineBreak = "\r\n")
        Assert.assertNotNull(parsed)
        System.out.println(parsed)
        Assert.assertEquals("{\n  \"k1\": \"v1\",\n  \"K2\": \"v2\",\n  \"k3\": \"v3\"\n}", parsed)
    }

    @Test
    fun parseSimpleFormWithParam() {
        val text = "session:\$session\n" +
                "madonvi:000.00.00.H19\n" +
                "service:DanhSachPAKNChoXuLy"
        var parsed = UltimateJsonParser.parseRawText(text)
        Assert.assertNotNull(parsed)
        System.out.println(parsed)
        Assert.assertEquals("{\"madonvi\":\"000.00.00.H19\",\"session\":\"\$session\",\"service\":\"DanhSachPAKNChoXuLy\"}", parsed)


        parsed = UltimateJsonParser.parseRawText(text, true)
        Assert.assertNotNull(parsed)
        System.out.println(parsed)
        Assert.assertEquals("{\n  \"madonvi\": \"000.00.00.H19\",\n  \"session\": \"\$session\",\n  \"service\": \"DanhSachPAKNChoXuLy\"\n}", parsed)
    }
}