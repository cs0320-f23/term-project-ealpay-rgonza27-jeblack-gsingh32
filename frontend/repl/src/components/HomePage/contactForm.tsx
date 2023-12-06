import React from "react";
import "../../styles/contactForm.css";

interface FormProps {
  onSubmit: (data: FormData) => void;
}

interface FormData {
  name: string;
  email: string;
  message: string;
}

function Form({ onSubmit }: FormProps) {
  const [formData, setFormData] = React.useState<FormData>({
    name: "",
    email: "",
    message: "",
  });

  function handleInputChange(event: React.ChangeEvent<HTMLInputElement>) {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  }

  function handleTextAreaChange(event: React.ChangeEvent<HTMLTextAreaElement>) {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  }

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    onSubmit(formData);
  }

  return (
    <div className="main-div">
      <form className="contact-form" onSubmit={handleSubmit}>
        <div className="input-group">
          <input
            className="input-bar-name"
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleInputChange}
          />
          <label htmlFor="name" className="label-name">
            Name:
          </label>
        </div>
        <br />
        <div className="input-group">
          <input
            className="input-bar-email"
            type="text"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleInputChange}
          />
          <label htmlFor="email" className="label-email">
            Email:
          </label>
        </div>
        <br />
        <div className="input-group">
          <textarea
            className="input-bar-message"
            id="message"
            name="message"
            value={formData.message}
            onChange={handleTextAreaChange}
          />
          <label htmlFor="message" className="label-message">
            Message:
          </label>
        </div>
        <br />
        <button type="submit">Submit</button>
      </form>
    </div>
  );
}

export default Form;
