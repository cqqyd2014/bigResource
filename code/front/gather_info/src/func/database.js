

var database = {

    baseparameter(json_str){
        
        let _array=json_str.data ;
        let _new_array=[];
        _array.map(function(item,key,ary) {
            let o={key:item.pk.pcode,value:item.pvalue};
            _new_array.push(o);
            
            
       });
       //console.log(_new_array);
       return _new_array;
        
    }

}

export default database;