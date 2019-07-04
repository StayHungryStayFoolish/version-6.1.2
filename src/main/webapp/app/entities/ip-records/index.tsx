import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IPRecords from './ip-records';
import IPRecordsDetail from './ip-records-detail';
import IPRecordsUpdate from './ip-records-update';
import IPRecordsDeleteDialog from './ip-records-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IPRecordsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IPRecordsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IPRecordsDetail} />
      <ErrorBoundaryRoute path={match.url} component={IPRecords} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={IPRecordsDeleteDialog} />
  </>
);

export default Routes;
