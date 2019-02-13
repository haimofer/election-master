import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

const API = "http://localhost:8080/";
const OPTIONS = { headers : { 'Content-Type' : 'application/json' } }

@Injectable()
export class HttpService {

  constructor(private httpClient: HttpClient) {
  }

  getCampaigns() {
    return this.httpClient.get(API+'election/campaign', OPTIONS);
  }
  postCurrentVote(body){
    return this.httpClient.post(API+'election/postCurrentVote',body, OPTIONS);
  }
}
