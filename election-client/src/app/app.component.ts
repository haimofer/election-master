import {Component, OnInit, Inject, ElementRef, ViewChild} from '@angular/core';
import {HttpService} from './HttpService';
import {enableProdMode} from '@angular/core';

enableProdMode();

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    name: string = 'N/A';
    topTen: any = [];

    constructor(private httpService: HttpService) {
    }

    ngOnInit(): void {
        this.refreshData();
    }

    public barChartOptions: any = {
        scaleShowVerticalLines: false,
        responsive: true
    };
    public barChartLabels: string[] = [];
    public barChartType: string = 'bar';
    public barChartLegend: boolean = true;
    public barChartColors: Array<any> = [
        { // dark grey
            backgroundColor: 'rgba(77,83,96,0.2)',
            borderColor: 'rgba(77,83,96,1)',
            pointBackgroundColor: 'rgba(77,83,96,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(77,83,96,1)'
        }
    ];

    public barChartData: any[] = [
        {data: [], label: 'Top 10 Candidates'}
    ];

    // events
    public chartClicked(e: any): void {
        console.log(e);
    }

    public chartHovered(e: any): void {
        console.log(e);
    }

    public refreshData() {
        this.httpService.getCampaigns().subscribe((response) => {
            if (response != null) {
                this.name = Object.keys(response)[0];

                //update chartData
                let clone = JSON.parse(JSON.stringify(this.barChartData));
                clone[0].data = Object.values(response[this.name]);
                this.barChartData = clone;
                //console.log("data:" + this.barChartData[0].data);

                //update chartLabels
                this.barChartLabels.length = 0;
                this.barChartLabels.push(...Object.keys(response[this.name]));
                //console.log("labels: " + this.barChartLabels);
            }
        }, (error) => {
            console.log(error)
        })
    }

    public upsertVote() {
        let details = {
            "userId": parseInt(document.getElementById("userId").value) || 0,
            "pass": document.getElementById("pass").value || "",
            "vote": parseInt(document.getElementById("currentVote").value) || 0
        };
        //console.log(details);
        this.httpService.postCurrentVote(details).subscribe((response) => {
            this.refreshData();
        }, (error) => {
            console.log(error);
        })
    };

}
