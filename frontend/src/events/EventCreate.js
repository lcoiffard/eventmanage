import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../AppNavbar';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";



class EventCreate extends Component {

    emptyItem = {
        name: '',
        description: '',
        beginDate: '',
        endDate: '',
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        const getLocale = locale => require(`date-fns/locale/${window.navigator.userLanguage || window.navigator.language}/index.js`);
        this.locale = getLocale(window.navigator.userLanguage || window.navigator.language);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = { ...this.state.item };
        item[name] = value;
        this.setState({ item });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const { item } = this.state;

        await fetch('/api/events', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/events');
    }

    render() {
        const { item } = this.state;
        const title = <h2>{'Add event'}</h2>;

        return <div>
            <AppNavbar />
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit} role="event-create-form">
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" maxLength={32} required
                            onChange={this.handleChange} autoComplete="name" />
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="text" name="description" id="description" required
                            onChange={this.handleChange} autoComplete="description" />
                    </FormGroup>
                    <FormGroup>
                        <Label for="beginDate">Begin date</Label>
                        <DatePicker name="beginDate" id="beginDate"
                            selected={item.beginDate}
                            locale={this.locale}
                            dateFormat='Pp'
                            showTimeSelect
                            onChange={(value) => this.handleChange({ target: { value, name: "beginDate" } })} required />
                    </FormGroup>
                    <FormGroup>
                        <Label for="endDate">End date</Label>
                        <DatePicker name="endDate" id="endDate"
                            selected={item.endDate}
                            locale={this.locale}
                            dateFormat='Pp'
                            showTimeSelect
                            onChange={(value) => this.handleChange({ target: { value, name: "endDate" } })} required />
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/events">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(EventCreate);