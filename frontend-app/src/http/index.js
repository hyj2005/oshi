// 抽取axios发请求的方法
import axios from "axios";
const http = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 3000,
  headers: {'X-Custom-Header': 'foobar'}
});


// 添加请求拦截器
http.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器
http.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    //原生的响应对象中的data才是服务器返回的数据
    return response.data;//直接把服务器的数据交给后来的人
  }, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
  });

  export default http;