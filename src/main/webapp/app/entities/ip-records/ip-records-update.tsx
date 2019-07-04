import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './ip-records.reducer';
import { IIPRecords } from 'app/shared/model/ip-records.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IIPRecordsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IIPRecordsUpdateState {
  isNew: boolean;
}

export class IPRecordsUpdate extends React.Component<IIPRecordsUpdateProps, IIPRecordsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { iPRecordsEntity } = this.props;
      const entity = {
        ...iPRecordsEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/ip-records');
  };

  render() {
    const { iPRecordsEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="monolithicApp.iPRecords.home.createOrEditLabel">
              <Translate contentKey="monolithicApp.iPRecords.home.createOrEditLabel">Create or edit a IPRecords</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : iPRecordsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="ip-records-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ip-records-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="userIdLabel" for="ip-records-userId">
                    <Translate contentKey="monolithicApp.iPRecords.userId">User Id</Translate>
                  </Label>
                  <AvField
                    id="ip-records-userId"
                    type="string"
                    className="form-control"
                    name="userId"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="deviceLabel" for="ip-records-device">
                    <Translate contentKey="monolithicApp.iPRecords.device">Device</Translate>
                  </Label>
                  <AvField
                    id="ip-records-device"
                    type="text"
                    name="device"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="ipAddressLabel" for="ip-records-ipAddress">
                    <Translate contentKey="monolithicApp.iPRecords.ipAddress">Ip Address</Translate>
                  </Label>
                  <AvField
                    id="ip-records-ipAddress"
                    type="text"
                    name="ipAddress"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="countryNameLabel" for="ip-records-countryName">
                    <Translate contentKey="monolithicApp.iPRecords.countryName">Country Name</Translate>
                  </Label>
                  <AvField id="ip-records-countryName" type="text" name="countryName" />
                </AvGroup>
                <AvGroup>
                  <Label id="cityNameLabel" for="ip-records-cityName">
                    <Translate contentKey="monolithicApp.iPRecords.cityName">City Name</Translate>
                  </Label>
                  <AvField id="ip-records-cityName" type="text" name="cityName" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="ip-records-status">
                    <Translate contentKey="monolithicApp.iPRecords.status">Status</Translate>
                  </Label>
                  <AvField id="ip-records-status" type="text" name="status" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ip-records" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  iPRecordsEntity: storeState.iPRecords.entity,
  loading: storeState.iPRecords.loading,
  updating: storeState.iPRecords.updating,
  updateSuccess: storeState.iPRecords.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(IPRecordsUpdate);
