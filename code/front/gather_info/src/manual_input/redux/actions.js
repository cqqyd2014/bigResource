import * as ActionTypes from './actionTypes';

export const comNameChangeAction = nameValue => ({
    type: ActionTypes.COM_NAME_CHANGE,
    payload: {
        comName:nameValue
    }
  });
  export const comUSCCChangeAction = usccValue => ({
    type: ActionTypes.COM_USCC_CHANGE,
    payload: {
        comUSCC:usccValue
    }
  });
