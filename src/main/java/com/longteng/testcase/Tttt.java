package com.longteng.testcase;

import com.longteng.framework.asserts.AssertUtil;
import org.testng.annotations.Test;

public class Tttt extends TestCase {

    @Test
    public void a() {
//        driver.get("http://www.baidu.com");
        String rrr = GetData("入参");
        SetData("龙腾虎跃",rrr);
        AssertUtil.assertEquals(rrr, rrr, "bbb");
        SetData("描述", "描述");
//        AssertUtil.assertEquals("3", "4", "cccc");
    }
}
