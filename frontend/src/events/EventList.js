import React, { Component } from 'react';
import { Button, Container, Table } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import { Link } from 'react-router-dom';
import moment from 'moment/min/moment-with-locales';

class EventList extends Component {

    constructor(props) {
        super(props);
        this.state = { events: [] };
        this.locale = window.navigator.userLanguage || window.navigator.language;
    }

    convertDate(date) {

        return date ? moment(date).locale(this.locale).format('LLL') : '';

    }

    componentDidMount() {
        fetch('/api/events')
            .then(response => response.json())
            .then(data => this.setState({ events: data }));
    }



    render() {
        const { events } = this.state;

        const eventList = events.map(event => {
            return <tr key={event.id} role="listitem">
                <td style={{ whiteSpace: 'nowrap' }}>{event.name}</td>
                <td>{event.description}</td>
                <td>{this.convertDate(event.beginDate)}</td>
                <td>{this.convertDate(event.endDate)}</td>
            </tr>
        });

        return (
            <div>
                <AppNavbar />
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/events/new">Add Event</Button>
                    </div>
                    <h3>Events</h3>
                    <Table className="mt-4" role="event-list">
                        <thead>
                            <tr>
                                <th width="30%">Name</th>
                                <th width="30%">Description</th>
                                <th width="20%">Begin date</th>
                                <th width="20%">End date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {eventList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default EventList;