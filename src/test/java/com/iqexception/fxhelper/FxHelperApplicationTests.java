package com.iqexception.fxhelper;

import com.iqexception.fxhelper.dal.generator.tables.pojos.FxUser;
import com.iqexception.fxhelper.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class FxHelperApplicationTests {

  @Test
  void contextLoads() {}

  @Autowired private UserService fxUserService;

  @Test
  public void testSelect() {
    System.out.println(("----- selectAll method test ------"));
    FxUser user =  fxUserService.getLoginUser(1L);
    Assert.isTrue(1 == user.getUserId(), "");
  }
}
