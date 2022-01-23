import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import EventList from './events/EventList';
import EventCreate from "./events/EventCreate";

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home} />
          <Route path='/events' exact={true} component={EventList} />
          <Route path='/events/new' component={EventCreate} />
        </Switch>
      </Router>
    )
  }
}

export default App;