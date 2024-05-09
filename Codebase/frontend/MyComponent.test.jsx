import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';

const MyComponent = ({ title, onClick }) => {
  return (
    <div>
      <h1 className="text-2xl font-bold">{title}</h1>
      <button
        className="bg-blue-500 text-white px-4 py-2 rounded"
        onClick={onClick}
      >
        Click me
      </button>
    </div>
  );
};

describe('MyComponent', () => {
  test('renders correctly with given title', () => {
    const title = 'Test Title';
    render(<MyComponent title={title} />);
    const heading = screen.getByRole('heading', { level: 1 });
    expect(heading).toHaveTextContent(title);
  });

  test('applies Tailwind CSS classes to button', () => {
    render(<MyComponent />);
    const button = screen.getByRole('button');
    expect(button).toHaveClass('bg-blue-500');
    expect(button).toHaveClass('text-white');
    expect(button).toHaveClass('px-4');
    expect(button).toHaveClass('py-2');
    expect(button).toHaveClass('rounded');
  });

  test('calls onClick when button is clicked', () => {
    const onClickMock = jest.fn();
    render(<MyComponent onClick={onClickMock} />);
    const button = screen.getByRole('button');
    fireEvent.click(button);
    expect(onClickMock).toHaveBeenCalledTimes(1);
  });
});
