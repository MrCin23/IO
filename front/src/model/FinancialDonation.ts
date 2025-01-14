export class FinancialDonation{
    id: number;
    donorId: number;
    needId: number;
    needName: string;
    date: Date;
    amount: number;
    currency: string;
    status: string;

    constructor(
        id: number,
        donorId: number,
        needId: number,
        needName: string,
        date: Date,
        amount: number,
        currency: string,
        status: string) {

        this.id = id
        this.donorId = donorId
        this.needId = needId
        this.needName = needName
        this.date = date
        this.amount = amount
        this.currency = currency
        this.status = status
    }
}