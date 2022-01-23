import { render, fireEvent, screen, waitFor } from '@testing-library/react'
import { BrowserRouter as Router } from 'react-router-dom';
import App from '../App';
import { rest } from 'msw'
import { setupServer } from 'msw/node'

const server = setupServer(
  rest.post('/api/events', (req, res, ctx) => {
    return res(ctx.json(
      {
        "id": 3,
        "name": "event 3",
        "description": "event number three",
        "beginDate": "2022-01-23T11:00:00.029-05:00",
        "endDate": "2022-01-23T12:00:00.11-05:00"
      }))
  }),
  rest.get('/api/events', (req, res, ctx) => {
    return res(ctx.json([
      {
        "id": 1,
        "name": "event 1",
        "description": "event number one",
        "beginDate": "2022-01-23T11:00:00.029-05:00",
        "endDate": "2022-01-23T12:00:00.11-05:00"
      },
      {
        "id": 2,
        "name": "event 2",
        "description": "event number two",
        "beginDate": "2022-01-24T10:00:00.029-05:00",
        "endDate": "2022-01-25T18:00:00.11-05:00"
      },
    ]))
  }),
)

beforeAll(() => server.listen())
afterEach(() => server.resetHandlers())
afterAll(() => server.close())

test('when click add event link then show create form', async () => {
  window.history.pushState({}, 'Add event', "/events/new")
  render(<App />, { wrapper: Router });
  await waitFor(() => screen.getByRole("event-create-form"));
});

test('given no fields filled when submit then stay', async () => {
  window.history.pushState({}, 'Add event', "/events/new")
  render(<App />, { wrapper: Router });
  await waitFor(() => screen.getByRole("event-create-form"))

  fireEvent.click(screen.getByText(/Save/i));
  await waitFor(() => screen.getByRole("event-create-form"))

});

test('given all fields filled when submit then go to events list', async () => {
  window.history.pushState({}, 'Add event', "/events/new")
  render(<App />, { wrapper: Router });
  await waitFor(() => screen.getByRole("event-create-form"))

  fireEvent.change(screen.getByLabelText("Name"), { target: { value: 'event 3' } })
  fireEvent.change(screen.getByLabelText("Description"), { target: { value: 'event number three' } })
  fireEvent.change(screen.getByLabelText("Begin date"), { target: { value: '2022-01-23T11:00:00.029-05:00' } })
  fireEvent.change(screen.getByLabelText("End date"), { target: { value: '2022-01-23T12:00:00.11-05:00' } })

  fireEvent.click(screen.getByText(/Save/i));

  await waitFor(() => screen.getByRole("event-list"));
});