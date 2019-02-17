import * as ActionTypes from './actionTypes.js';


export default (state = {}, action) => {
  switch(action.type) {
    case ActionTypes.COM_NAME_CHANGE: {
      return Object.assign({},state,{"manualInputForm_ComName":action.payload.comName});
    }
    case ActionTypes.COM_USCC_CHANGE: {
        return Object.assign({},state,{"manualInputForm_ComUSCC":action.payload.comUSCC});
      }
    default:
      return state;
  }
}