import * as ActionTypes from './actionTypes.js';
export default (state = {}, action) => {
    switch(action.type) {
      case ActionTypes.DYNAMICCOL_VALUE_CHANGE: {
          //console.log("sfefef");
          let old_form_values=state.dynamic_form_values;
          let new_value=({[action.payload.col_name]:action.payload.col_value})
          
          /* old_form_values=Object.is(old_form_values,undefined)?{}:old_form_values;
          console.log(old_form_values) */
          let new_form_values=Object.assign({},old_form_values,new_value)
          //console.log(new_form_values);
          let new_state=Object.assign({},state,{dynamic_form_values:new_form_values});
          //console.log(new_state);
        return new_state;
      }
      
      default:
        return state;
    }
  }