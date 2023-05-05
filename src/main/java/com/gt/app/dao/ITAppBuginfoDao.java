package com.gt.app.dao;

import com.gt.model.*;
import org.springframework.data.jpa.repository.*;

/**
 *
 * @功能说明：缺陷记录表自定义JPA-DAO接口
 * @作者：liutaok
 * @创建日期：2023-04-27
 * @版本号：V1.0
 */
public interface ITAppBuginfoDao extends JpaRepository<TAppBuginfo, Integer> {

}
