import * as ActionTypes from './actionTypes';
export const colValueChangeAction = (col_name,col_value) => ({
    type: ActionTypes.DYNAMICCOL_VALUE_CHANGE,
    payload: {
        col_name:col_name,
        col_value:col_value
    }
  });