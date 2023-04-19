import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io';
import { Nav, Button } from './Pagination.styles';

interface PaginationProps {
  total: number;
  limit: number;
  page: number;
  setPage: React.Dispatch<React.SetStateAction<number>>;
}
const Pagination = ({ total, limit, page, setPage }) => {
  const numPages = Math.ceil(total / limit);

  return (
    <>
      <Nav>
        <Button onClick={() => setPage(page - 1)} disabled={page === 1}>
          <IoIosArrowBack size={20} />
        </Button>
        {[...Array(numPages)].map((_, i) => (
          <Button
            key={i + 1}
            onClick={() => setPage(i + 1)}
            aria-current={page === i + 1 ? 'page' : null}
          >
            {i + 1}
          </Button>
        ))}
        <Button onClick={() => setPage(page + 1)} disabled={page === numPages}>
          <IoIosArrowForward size={20} />
        </Button>
      </Nav>
    </>
  );
};

export default Pagination;
