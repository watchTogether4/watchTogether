export interface ThemeProps {
  theme: {
    color: {
      [key in string]: string;
    };
  };
}
