import React from 'react';

var html = {

    select(_array){
        
        
        
        _array.map(function(item,key,ary) {
            return (<option key={item.key} value={item.key}>
            {item.value}
          </option>);
            
            
       });
      
        
    }

}

export default html;