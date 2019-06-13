
//status
function get(key){
  var status = wx.getStorageSync(key);
  //没取到
  if (!status) {
    var status = getApp().globalData[key];
  }
  return status;
}

module.exports = {
  get
}