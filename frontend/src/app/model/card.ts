import { CardSuit } from './cardSuit';
import { CardType } from './cardType';

export interface Card {
  suit: CardSuit;
  type: CardType;
}
