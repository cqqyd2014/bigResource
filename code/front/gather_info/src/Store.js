import {createStore,combineReducers} from 'redux';
import {reducer as manualInputReducer} from './manual_input';
import {reducer as dynamicFormReducer} from './dynamicForm';
//import { reducer as formReducer } from 'redux-form'

const reducer=combineReducers({
    manualInputReducer: manualInputReducer,
    dynamicForm:dynamicFormReducer,
})
const store=createStore(reducer);
export default store;
