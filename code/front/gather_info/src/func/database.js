

var database = {

    baseparameter(json_str){
        
        let _array=json_str.data ;
        let _new_array=[];
        _array.map((item,key,ary)=>{
            let o={value:item.pk.pcode,text:item.pvalue};
            
            
            return _new_array.push(o);
       });
       return _new_array;
        
    }

}

export default database;