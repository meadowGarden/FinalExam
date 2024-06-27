import { create } from "zustand";
import { persist } from "zustand/middleware";

const useUserStore = create(
  persist(
    (set) => ({
      user: {
        firstName: "",
        lastName: "",
        email: "",
        userRole: "",
        token: "",
      },
      setUser: (userData) => {
        set({
          user: {
            firstName: userData.firstName,
            lastName: userData.lastName,
            email: userData.email,
            userRole: userData.userRole,
            token: userData.token,
          },
        });
      },
      clearUser: () => {
        set({
          user: {
            firstName: "",
            lastName: "",
            email: "",
            userRole: "",
            token: "",
          },
        });
      },
    }),
    { name: "userStorage" }
  )
);

export default useUserStore;

// import { create } from "zustand";

// export const useUserStore = create((set) => ({
//   user: {
//     firstName: "",
//     lastName: "",
//     email: "",
//     userRole: "",
//     token: "",
//   },
//   setUser: (userData) =>
//     set({
//       user: {
//         firstName: userData.firstName,
//         lastName: userData.lastName,
//         email: userData.email,
//         userRole: userData.userRole,
//         token: userData.token,
//       },
//     }),
//   clearUser: () =>
//     set({
//       user: {
//         firstName: "",
//         lastName: "",
//         email: "",
//         userRole: "",
//         token: "",
//       },
//     }),
// }));
