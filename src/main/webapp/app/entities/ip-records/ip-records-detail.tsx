import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ip-records.reducer';
import { IIPRecords } from 'app/shared/model/ip-records.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIPRecordsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class IPRecordsDetail extends React.Component<IIPRecordsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { iPRecordsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="monolithicApp.iPRecords.detail.title">IPRecords</Translate> [<b>{iPRecordsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="userId">
                <Translate contentKey="monolithicApp.iPRecords.userId">User Id</Translate>
              </span>
            </dt>
            <dd>{iPRecordsEntity.userId}</dd>
            <dt>
              <span id="device">
                <Translate contentKey="monolithicApp.iPRecords.device">Device</Translate>
              </span>
            </dt>
            <dd>{iPRecordsEntity.device}</dd>
            <dt>
              <span id="ipAddress">
                <Translate contentKey="monolithicApp.iPRecords.ipAddress">Ip Address</Translate>
              </span>
            </dt>
            <dd>{iPRecordsEntity.ipAddress}</dd>
            <dt>
              <span id="countryName">
                <Translate contentKey="monolithicApp.iPRecords.countryName">Country Name</Translate>
              </span>
            </dt>
            <dd>{iPRecordsEntity.countryName}</dd>
            <dt>
              <span id="cityName">
                <Translate contentKey="monolithicApp.iPRecords.cityName">City Name</Translate>
              </span>
            </dt>
            <dd>{iPRecordsEntity.cityName}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="monolithicApp.iPRecords.status">Status</Translate>
              </span>
            </dt>
            <dd>{iPRecordsEntity.status}</dd>
          </dl>
          <Button tag={Link} to="/entity/ip-records" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/ip-records/${iPRecordsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ iPRecords }: IRootState) => ({
  iPRecordsEntity: iPRecords.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(IPRecordsDetail);
