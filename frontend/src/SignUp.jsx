import React, { useState } from "react";
import { FaFacebook, FaGoogle } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";
import { auth, db } from "./firebase";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { doc, setDoc } from "firebase/firestore";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const SignUp = () => {
  const [profileName, setProfileName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [gender, setGender] = useState("");
  const [birthMonth, setBirthMonth] = useState("");
  const [birthDate, setBirthDate] = useState("");
  const [birthYear, setBirthYear] = useState("");
  const [shareRegistrationData, setShareRegistrationData] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const userCredential = await createUserWithEmailAndPassword(
        auth,
        email,
        password
      );
      const user = userCredential.user;
      await setDoc(doc(db, "users", user.uid), {
        profileName,
        email,
        gender,
        dateOfBirth: `${birthMonth}/${birthDate}/${birthYear}`,
        shareRegistrationData,
      });
      console.log("User registered successfully");
      navigate("/onboarding");
    } catch (error) {
      console.error("Error registering user:", error);
    }
  };

  return (
    <div className="flex min-h-screen flex-col items-center justify-center bg-gray-100">
      <div className="w-full max-w-md rounded-lg bg-white px-8 py-10 shadow-md">
        <h2 className="mb-8 text-center text-3xl font-bold">
          Sign up for free to start getting book recommendations
        </h2>
        <div className="mb-8 flex justify-center space-x-6">
          <button className="rounded-md bg-blue-500 p-3 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
            <FaFacebook className="h-6 w-6" />
          </button>
          <button className="rounded-md bg-red-500 p-3 text-white hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-500">
            <FaGoogle className="h-6 w-6" />
          </button>
        </div>
        <div className="mb-8 flex items-center">
          <hr className="w-full border-gray-300" />
          <span className="mx-4 text-gray-400">OR</span>
          <hr className="w-full border-gray-300" />
        </div>
        <form onSubmit={handleSubmit}>
          <div className="mb-6">
            <input
              type="text"
              placeholder="Enter your profile name"
              value={profileName}
              onChange={(e) => setProfileName(e.target.value)}
              className="w-full rounded-md border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div className="mb-6">
            <input
              type="email"
              placeholder="Enter your email address"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full rounded-md border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          <div className="mb-6">
            <input
              type="password"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full rounded-md border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <p className="mt-2 text-xs text-gray-500">
              Use 8 or more characters with a mix of letters, numbers & symbols.
            </p>
          </div>
          <div className="mb-6">
            <p className="mb-4 text-sm text-gray-600">
              What's your gender? (optional)
            </p>
            <div className="flex items-center space-x-6">
              <label className="flex items-center">
                <input
                  type="radio"
                  name="gender"
                  value="Female"
                  checked={gender === "Female"}
                  onChange={(e) => setGender(e.target.value)}
                  className="mr-2 h-4 w-4 text-blue-500 focus:ring-blue-500"
                />
                <span className="text-gray-600">Female</span>
              </label>
              <label className="flex items-center">
                <input
                  type="radio"
                  name="gender"
                  value="Male"
                  checked={gender === "Male"}
                  onChange={(e) => setGender(e.target.value)}
                  className="mr-2 h-4 w-4 text-blue-500 focus:ring-blue-500"
                />
                <span className="text-gray-600">Male</span>
              </label>
              <label className="flex items-center">
                <input
                  type="radio"
                  name="gender"
                  value="Non-binary"
                  checked={gender === "Non-binary"}
                  onChange={(e) => setGender(e.target.value)}
                  className="mr-2 h-4 w-4 text-blue-500 focus:ring-blue-500"
                />
                <span className="text-gray-600">Non-binary</span>
              </label>
            </div>
          </div>
          <div className="mb-6">
  <p className="mb-4 text-sm text-gray-600">What's your date of birth?</p>
  <DatePicker
    selected={
      birthYear && birthMonth && birthDate
        ? new Date(birthYear, birthMonth - 1, birthDate)
        : null
    }
    onChange={(date) => {
      setBirthYear(date.getFullYear());
      setBirthMonth(date.getMonth() + 1);
      setBirthDate(date.getDate());
    }}
    dateFormat="yyyy-MM-dd"
    className="w-full rounded-md border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
  />
</div>
          <div className="mb-8">
            <label className="flex items-center">
              <input
                type="checkbox"
                checked={shareRegistrationData}
                onChange={(e) => setShareRegistrationData(e.target.checked)}
                className="mr-2 h-4 w-4 text-blue-500 focus:ring-blue-500"
              />
              <span className="text-gray-600">
                Share my registration data with our content providers for
                marketing purposes.
              </span>
            </label>
          </div>
          <button
            type="submit"
            className="mb-6 w-full rounded-md bg-blue-500 px-4 py-2 text-white hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Sign Up
          </button>
        </form>
        <div className="text-center text-sm text-gray-600">
          Already have an account?{" "}
          <Link to="/signin">
            <button className="font-bold text-blue-500 hover:underline">
              Log in
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default SignUp;