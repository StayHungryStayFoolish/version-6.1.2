import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IIPRecords, defaultValue } from 'app/shared/model/ip-records.model';

export const ACTION_TYPES = {
  FETCH_IPRECORDS_LIST: 'iPRecords/FETCH_IPRECORDS_LIST',
  FETCH_IPRECORDS: 'iPRecords/FETCH_IPRECORDS',
  CREATE_IPRECORDS: 'iPRecords/CREATE_IPRECORDS',
  UPDATE_IPRECORDS: 'iPRecords/UPDATE_IPRECORDS',
  DELETE_IPRECORDS: 'iPRecords/DELETE_IPRECORDS',
  RESET: 'iPRecords/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IIPRecords>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type IPRecordsState = Readonly<typeof initialState>;

// Reducer

export default (state: IPRecordsState = initialState, action): IPRecordsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_IPRECORDS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_IPRECORDS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_IPRECORDS):
    case REQUEST(ACTION_TYPES.UPDATE_IPRECORDS):
    case REQUEST(ACTION_TYPES.DELETE_IPRECORDS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_IPRECORDS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_IPRECORDS):
    case FAILURE(ACTION_TYPES.CREATE_IPRECORDS):
    case FAILURE(ACTION_TYPES.UPDATE_IPRECORDS):
    case FAILURE(ACTION_TYPES.DELETE_IPRECORDS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_IPRECORDS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_IPRECORDS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_IPRECORDS):
    case SUCCESS(ACTION_TYPES.UPDATE_IPRECORDS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_IPRECORDS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/ip-records';

// Actions

export const getEntities: ICrudGetAllAction<IIPRecords> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_IPRECORDS_LIST,
    payload: axios.get<IIPRecords>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IIPRecords> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_IPRECORDS,
    payload: axios.get<IIPRecords>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IIPRecords> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_IPRECORDS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IIPRecords> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_IPRECORDS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IIPRecords> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_IPRECORDS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
