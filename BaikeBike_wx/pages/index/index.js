//导包
var myUtils = require("../../utils/myUtils.js")

Page({
  data: {
    log:0.0,
    lat:0.0,
    // relog:0.0,
    // relat:0.0,
    controls: [],
    markers:[],
  },
  
  //生命周期函数--监听页面加载
  onLoad: function () {
    var that= this;
    wx.getLocation({
      success: function(res) {
        var longitude = res.longitude
        var latitude = res.latitude
        that.setData({
          log : longitude,
          lat : latitude
        })
      },
    }) 

    wx.getSystemInfo({
      success: function(res) {
        //res是手机的参数
        var windowWidth = res.windowWidth;
        var windowHeight = res.windowHeight;
        that.setData({
          controls:[
            
            {
              //扫码按钮
              id: 1,
              //控件（按钮）的背景图片
              iconPath: '/images/lock.png',
              //控件的相对页面位置
              position: {
                width: 100,
                height: 50,
                left: windowWidth / 2 - 50,
                top: windowHeight - 60
              },
              //是否可点击
              clickable: true
            },  

            {
              //定位按钮
              id:2,
              iconPath: '/images/locate.png',
              position:{
                width:40,
                height:40,
                left:10,
                top:windowHeight-50
              },
              //是否可点击
              clickable:true
            },
            
            {
              //中心点位置
              id: 3,
              iconPath: '/images/cur.png',
              position: {
                width: 30,
                height: 35,
                left: windowWidth / 2 - 10,
                top: windowHeight / 2 - 40.
              },
              //是否可点击
              clickable: true
            },

            {
              //充值按钮
              id: 4,
              iconPath: '/images/chongzhi.png',
              position: {
                width: 40,
                height: 40,
                left: windowWidth - 45,
                top: windowHeight - 120
              },
              clickable: true
            },

            {
              //添加车辆
              id: 5,
              iconPath: '/images/add.png',
              position: {
                width: 35,
                height: 35,
              
              },
              clickable: true
            }, 

            {
              //报修按钮
              id: 6,
              iconPath: '/images/baojing.png',
              position: {
                width: 35,
                height: 35,
                left: windowWidth - 42,
                top: windowHeight - 50
              },
              clickable: true
            },  
            
          ]
        })
      },
    })
  },

  /**
   * 控件被点击事件
   */
  controltap: function(e){
    var that = this;
    var cid = e.controlId;
    switch(cid){

      //点击扫码
      case 1:{
        var status = myUtils.get("status");
        //根据用户的状态，来跳转到对应页面
      
        //如果是0，跳转到手机注册页面
        if(status == 0){
         wx.navigateTo({
           url: '../register/register',
         })
        } else if(status == 1){
          wx.navigateTo({
            url: '../deposit/deposit',
          })
        }
        break
      }

      //点定位按钮
      case 2:{
        this.mapCtx.moveToLocation()
        break;
      }

      //添加车辆
      case 5:{
        //获取当前已有的车辆
        // var bikes = that.data.markers;
        //获取到移动后位置的中心点
        this.mapCtx.getCenterLocation({
          success:function(res){
            var log = res.longitude;
            var lat = res.latitude;
            //在移动后的位置添加一辆单车
            // bikes.push({
            //   iconPath: "/images/bike@red.png",
            //   width:35,
            //   height:40,
            //   longitude:log,
            //   latitude:lat
            // })
            // //重新赋值
            // that.setData({
            //   markers:bikes
            // })
            //发送请求： 将添加的单车数据发送到后台(springboot)
            wx.request({
              url: 'http://localhost:8080/bike/add',
              data:{
                longitude:log,
                latitude:lat,
                bikeNo:100010,
                status:0
              },
              method:"POST",
              success:function(res){
                console.log(res)
              }

            })

          }
        })
        
        break;
      }
    }

  },


  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function(){
    //创建map上下文
    this.mapCtx = wx.createMapContext('myMap')
  },


  /**
   * 移动后地图视野发生变化触发的事件
   */
  regionchange:function(e){
    // var that = this;
    // //获取移动之后的位置
    // var etype = e.type;
    // if(etype == "end"){
    //   this.mapCtx.getCenterLocation({
    //     success:function(res){
    //       that.setData({
    //         relog:res.longitude,
    //         relat:res.latitude
    //       })
          
    //     }
    //   })
    // }
  },


})
