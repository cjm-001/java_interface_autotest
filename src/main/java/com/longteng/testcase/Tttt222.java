package com.longteng.testcase;

import com.longteng.framework.asserts.AssertUtil;
import org.testng.annotations.Test;

public class Tttt222 extends TestCase {

    @Test
    public void a() {
        String rrr =  GetData("入参");
        AssertUtil.assertEquals( "2", "2", "dddd");
        SetData("下单时间","就是现在");
        AssertUtil.assertEquals( "2", "2", "aaaaaaaa");
        AssertUtil.assertEquals( "2", "2", "aaaaaaasdfssda");
    }
}
