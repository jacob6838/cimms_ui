import KeycloakProvider from "next-auth/providers/keycloak";

const kaycloakProvider = KeycloakProvider({
  clientId: "React-auth",
  realm: "keycloak-react-auth",
  issuer: "http://localhost:8080/",
});

export default kaycloakProvider;
