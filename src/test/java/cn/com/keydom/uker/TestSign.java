package cn.com.keydom.uker;

import com.alibaba.fastjson.JSONObject;

import cn.com.czcb.pub.InitConfig;
import cn.com.czcb.pub.center.RSAEncrypt;
import cn.com.czcb.pub.tft.SignUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AJiong
 * @version $$Id: ${file_name}, v 0.1 ${date} ${time} Ajiong Exp $$
 */
public class TestSign {
    public static void main(String[] args) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("cardNo", "6100000173010308");
        String s = SignUtil.encodeBase64((SignUtil.sign256(JSONObject.toJSONString(map),
                RSAEncrypt.loadPrivateKeyByStr("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDgdqWc9k0T6nCTUBnYUaNIFgztiz6qqjQ+vlXtwPC/MylOxL/7QorUFivtiXRxIjQbRKXdo3JXkt9tbPtCw+Nzz2xLAImQuLIz1tFyqT/n0JRY4wvqc4KQHoBsDNla31zzM0hW87FCUrIHI92f3fBux6Au3vDj7f1CKw0BbVZKBxggQQ6SiBCHSrRBPot56iI+RkCRpJ3htuH8qN6ajKY6KiaGOAJQtX/wMKY6koKigxyqWgLNINnko4ruaBSoE5RPppEpHjT+9d3fQj9eMwxZCXjS+OTDeiSITh0bEiQLkP3Q6rgfXnRRnQFFI8Eex9C3zxNs6I/4/cYcjC4DOQdHAgMBAAECggEBAIxBtxSQVvNl37g9KhkgUg3+MU/RXXQY7JJ0Lo9kq0pd9qbQjEG9f7AstoA0Dr2OqM6NI7ONqSoNjWz71zkN3DeGWd231T3IGxoHVlL2bQc2Lywnm0+BpAjX9lkN4ldFEPiqh75FgfK2Z6XMhHSbotwmAvKmvhE+qazJ59OHdY7FGnfCrli9ohRoq4JudjXgAtQHoInw41RzAWmlIJA7jjSnFit2Q9r40oFW54sgC6LoeGqbFCmAam2HP/CdZ1pmOqqWQnv2W4dc8Y86FwpHDKel/MwJrEPhamiRCioFcgmar3NaOmVrFnXMcR4AnqV97FKOCo9gRKKE/zA5/2GW8nECgYEA8ia2XTp975jxGsS1e/s4FvPH5oHFaWjz8D6iwRKbwlfUg6At/Ckc/pumcJwoeuokByu2jJjiiehPDKHOVsOgtf7oXkZ6HT5iWP6kYt11vkleSYDnP8DtQn+N2c3NpxG43125upBNsZ+U3B3jcV8ZB+DVZTRkLf/s3lmD1G8tM78CgYEA7Uz4v1z0CZNee33iyFCNfoKCX8dI1sfAc2xHDaZ40TqNVLcp5D1PCtWdnUwy3P+kha5tW4g26Ug+FEoN2UZN11PYF22KjyFWmyzMTrAsoTs7AeXfm0+E7FL1UerKxgkTBOAXoVWkwZ1aVrYgxO+yLIcEmdnnVevtjj2jv79A7nkCgYEAoxpeNVeDohIDPIOItnTVJ+iWGfYI8WJHBOg7mg6dqX6dXvdSYgmTRQ+0QR99yKTTFZ1k/TixRjCapiiKojhslImZHH8eG3p8pfMG6KzAdSZYAux3VqLtbKAb/Lu5rLGsM4+e3UH7gwu5KbZcD239nyfatKtgWveKJY5n1Hv6F2MCgYAuyBXh4/lYSe0+ezrSMs8yOZhqOWdqsEaIergnQMJgvSJPqSxhCpUilapU8Ei2uM8+a3yzps7sfol+hnrkcq+125mmRLokLXZdcR92A9VIk9y2KxNCExMpY9FpLNh84h0VwPfnnnK0mMjOf2lGadq+XH/TtNmeXryeZhh6HMI4QQKBgC3MHgvtEeHcw98/93dayK/8CasK4DAo3FI0hpLpna9sbnlUlRoU84aduG46YEtCHJGlW8aFxiEf5zh92W5d+2+Vg46Cr25qAfON05WM9LFMOJXE0lPeBM2of9AsgcQMuL/e2pVTrvwFPJ4bn2lHaEx+Ruj3RVXRC39AgqYS40ed"))));
        System.out.println(s);
    }
}
