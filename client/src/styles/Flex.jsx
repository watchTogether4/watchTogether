import { css } from 'styled-components';

const Flex = css`
  display: flex;
  flex-direction: ${(props) => props.direction && props.direction};
  justify-content: ${(props) => (props.justifyContent ? props.justifyContent : 'center')};
  align-items: ${(props) => (props.alignItems ? props.alignItems : 'center')};
  flex-wrap: ${(props) => props.flexWrap || 'nowrap'};
  row-gap: ${(props) => props.rowGap && props.rowGap};
  column-gap: ${(props) => props.columnGap && props.columnGap};
`;

export default Flex;
