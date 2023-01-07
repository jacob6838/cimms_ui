import NextAuth from "next-auth"
import KeycloakProvider from "next-auth/providers/keycloak";
export const authOptions = {
  // Configure one or more authentication providers
  providers: [
    KeycloakProvider({
        clientId: "React-auth",
        clientSecret: "client-secret",
        issuer: "http://localhost:8080/",
      }),
    // ...add more providers here
  ],
}
export default NextAuth(authOptions)