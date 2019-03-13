import React from 'react';

var html = {

    select_potion(_array){
        
        
        
        _array.map(function(item,key,ary) {
            return (<option key={key} value={item.alue}>
            {item.text}
          </option>);
            
            
       });
      
        
    }

}

export default html;