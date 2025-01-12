
type FetchData = {
    method: string;
    mode: RequestMode;
    cache: RequestCache;
    headers: HeadersInit;
    redirect: RequestRedirect;
    referrerPolicy: ReferrerPolicy;
    signal: AbortSignal
    body?: string;
};

const getDefaultRequest = (method:string,data?:any)=>{
    const fetchData : FetchData = {
    method: method,
    mode: 'cors',
    cache: 'no-cache',
    headers: {
        'Access-Control-Allow-Origin':'*',
        
    },
    signal:AbortSignal.timeout(5000),
    redirect: 'follow',
    referrerPolicy: 'no-referrer',
    };
    if (data) {
    fetchData.headers = {
        ...fetchData.headers,
        'Content-Type': 'application/json',
    };
    fetchData['body'] = JSON.stringify(data);
    }
    console.log(fetchData.headers)
    return fetchData
}

export default getDefaultRequest