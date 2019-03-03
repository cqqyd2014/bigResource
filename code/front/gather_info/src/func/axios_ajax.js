import axios from 'axios';

var axios_ajax = {

    post(URL,url,data,that,callback){
        axios({
            method:"POST",
            headers:{'Content-type':'application/json',},
            url:URL+url,
            data:data,
            //withCredentials:true
        }).then(function(res){
            //alert('post:'+res)
            console.log(url+'\tPost请求到:');
            console.log(res);
            //alert('post-response:'+res);
            callback(that,res);
            //ajax_get('/manage/getinfo',this);
        }).catch(function(error){
            alert('post失败')
            console.log(error);
        });
    },
    get(URL,url,that,callback){
        console.log("axios开始");
        console.log(URL);
        console.log(url);
        axios({
            method:"GET",
            headers:{'Content-type':'application/json',},
            url:URL+url,
            withCredentials:true
        }).then(function(res){
            console.log(url+'\tGet请求到:')
            console.log(res);
            //alert('get:'+this.res);
            callback(that,res);
    
        }).catch(function(error){
            alert('get下载失败')
            console.log(error);
        });
    },
    post_params(URL,url,data,that,callback=()=>{}){
        axios({
            method: 'post',
            url: URL+url,
            headers: {
                'Content-type': 'application/x-www-form-urlencoded',
            },
            params:data,
        })
        .then(function(res){
            //alert('post:'+res)
            console.log(url+'\tPost请求到:');
            console.log(res);
            //alert('post-response:'+res);
            callback(that,res);
            //ajax_get('/manage/getinfo',this);
        }).catch(function(error){
            alert('post失败')
            console.log(error);
        });


    }

}

export default axios_ajax;