package com.gt.app.dao;

import com.gt.model.*;
import org.springframework.data.jpa.repository.*;

/**
 *
 * @功能说明：项目人员表自定义JPA-DAO接口
 * @作者：zm
 * @创建日期：2020-04-11
 * @版本号：V1.0
 */
public interface ITAppProjectPersonDao extends JpaRepository<TAppProjectPerson, Integer> {

}
