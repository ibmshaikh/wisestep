import { useState } from "react";

export const UserContext = () => {
  const [user, setUser] = useState(() => {
    return localStorage.getItem("user");
  });

  const setToken = (user) => {
    localStorage.setItem("user", user);
    setUser(user);
  };

  return [user, setToken];
};
