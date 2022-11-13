import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Player } from '../../model/player';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PlayerService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  public getPlayers(): Observable<Player[]> {
    return this.http.get<Player[]>(`${this.apiServerUrl}/players`);
  }

  public addPlayer(player: Player): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/players/add`, player);
  }

  public updatePlayer(playerId: string, player: Player): Observable<void> {
    return this.http.put<void>(
      `${this.apiServerUrl}/players/update${playerId}`,
      player
    );
  }

  public removePlayer(playerId: string): Observable<void> {
    return this.http.delete<void>(
      `${this.apiServerUrl}/players/delete/${playerId}`
    );
  }
}
