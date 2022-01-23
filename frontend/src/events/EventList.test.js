import { render, fireEvent, screen, waitFor } from '@testing-library/react'
import { rest } from 'msw'
import { setupServer } from 'msw/node'
import { BrowserRouter as Router, } from 'react-router-dom';
import App from '../App';

const server = setupServer(
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


test('when click events link then show events', async () => {
  window.history.pushState({}, 'Events', "/events")
  render(<App />, { wrapper: Router });

  await waitFor(() => screen.getByRole("event-list"));

  const items = await screen.findAllByRole('listitem');
  expect(items).toHaveLength(2);

});

test('when add event then show create screen', async () => {

  window.history.pushState({}, 'Events', "/events")
  render(<App />, { wrapper: Router });

  await waitFor(() => screen.getByRole("event-list"));

  fireEvent.click(screen.getByText(/Add event/i));

  await waitFor(() => screen.getByRole("event-create-form"));


});


