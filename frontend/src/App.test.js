import { render, fireEvent, screen, waitFor } from '@testing-library/react'
import App from './App';


test('renders learn react link', () => {
  render(<App />);
  const linkElement = screen.getByText(/Events/i);
  expect(linkElement).toBeInTheDocument();
});

test('when click events link then show events list page', async () => {

  render(<App />);

  fireEvent.click(screen.getByText('Events'))

  await waitFor(() => screen.getByRole("event-list"))

});



