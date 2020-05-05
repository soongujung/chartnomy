import axios from 'axios';

export default axios.create({
  baseURL: '//localhost:8080/api',
  headers:{
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'application/x-www-form-urlencoded'
  },
  crossdomain: true,
  proxy:{
    host:'localhost',
    port:8080
  }
});
