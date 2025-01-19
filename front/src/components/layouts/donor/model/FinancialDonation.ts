export class FinancialDonation{
    id: number;
    donorId: number;
    needId: number;
    needDescription: string;
    donationDate: Date;
    amount: number;
    currency: string;
    resourceStatus: string;

    constructor(
        id: number,
        donorId: number,
        needId: number,
        needDescription: string,
        donationDate: Date,
        amount: number,
        currency: string,
        resourceStatus: string) {

        this.id = id
        this.donorId = donorId
        this.needId = needId
        this.needDescription = needDescription
        this.donationDate = donationDate
        this.amount = amount
        this.currency = currency
        this.resourceStatus = resourceStatus
    }
}