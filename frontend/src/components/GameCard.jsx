import { Gamepad2 } from 'lucide-react';

const GameCard = ({ game }) => {
  return (
    <div className="card shadow-sm border-0 h-100">
      <div className="position-relative">
        <img
          src={game.imageUrl || 'https://via.placeholder.com/300x400?text=No+Image'}
          className="card-img-top"
          alt={game.title}
          style={{ height: '250px', objectFit: 'cover' }}
        />
        <span className="position-absolute top-0 end-0 m-2 badge bg-dark opacity-75">
          {game.category?.name}
        </span>
      </div>
      <div className="card-body">
        <h5 className="card-title d-flex align-items-center gap-2">
          <Gamepad2 size={18} />
          {game.title}
        </h5>
        <p className="text-muted small mb-0">Platform: {game.platform || 'N/A'}</p>
      </div>
    </div>
  );
};

export default GameCard;