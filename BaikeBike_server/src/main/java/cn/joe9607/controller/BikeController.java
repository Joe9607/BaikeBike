package cn.joe9607.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.joe9607.pojo.Bike;
import cn.joe9607.service.BikeService;


/**
 * 标记这个类是用来接受请求和响应用户的一个控制器
 * 加上这个注解后，spring容器就会对它进行实例化
 * @author Administrator
 *
 */
@Controller
public class BikeController {
	
	//到spring容器中查找BikeService类型的实例，注入到bikecontroller实例中
	@Autowired
	private BikeService bikeService;
	
	@RequestMapping("/bike/add")
	@ResponseBody
	public String add(@RequestBody Bike bike) {
		//调用service层，将数据保存到MongoDB中
		bikeService.save(bike);
		return "success";
	}
}
