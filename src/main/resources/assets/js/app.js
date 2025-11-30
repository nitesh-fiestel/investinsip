// Base URL for API calls
const API_BASE_URL = 'http://localhost:8080';

async function calculateSIP() {
    const amount = parseFloat(document.getElementById('sipAmount').value);
    const rate = parseFloat(document.getElementById('sipRate').value);
    const years = parseInt(document.getElementById('sipYears').value);

    try {
        const response = await fetch(`${API_BASE_URL}/api/investment/sip?amount=${amount}&rate=${rate}&years=${years}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const result = await response.json();
        document.getElementById('sipResult').innerHTML = `
            <div class="alert alert-success">
                <h4>SIP Calculation Result</h4>
                <p>Total Investment: ₹${result.totalInvestment?.toLocaleString('en-IN') || 'N/A'}</p>
                <p>Estimated Returns: ₹${result.estimatedReturns?.toLocaleString('en-IN') || 'N/A'}</p>
                <p>Total Value: ₹${result.totalValue?.toLocaleString('en-IN') || 'N/A'}</p>
            </div>
        `;
    } catch (error) {
        console.error('Error in calculateSIP:', error);
        document.getElementById('sipResult').innerHTML = `
            <div class="alert alert-danger">
                Error calculating SIP: ${error.message}
            </div>
        `;
    }
}

async function calculateSWP() {
    const amount = parseFloat(document.getElementById('swpAmount').value);
    const rate = parseFloat(document.getElementById('swpRate').value);
    const years = parseInt(document.getElementById('swpYears').value);

    try {
        const response = await fetch(`${API_BASE_URL}/api/investment/swp?amount=${amount}&rate=${rate}&years=${years}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const result = await response.json();
        document.getElementById('swpResult').innerHTML = `
            <div class="alert alert-info">
                <h4>SWP Calculation Result</h4>
                <p>Principal Amount: ₹${amount.toLocaleString('en-IN')}</p>
                <p>Monthly Withdrawal: ₹${result.monthlyWithdrawal?.toLocaleString('en-IN') || 'N/A'}</p>
                <p>Total Withdrawn: ₹${result.totalWithdrawn?.toLocaleString('en-IN') || 'N/A'}</p>
                <p>Final Corpus: ₹${result.finalCorpus?.toLocaleString('en-IN') || 'N/A'}</p>
            </div>
        `;
    } catch (error) {
        console.error('Error in calculateSWP:', error);
        document.getElementById('swpResult').innerHTML = `
            <div class="alert alert-danger">
                Error calculating SWP: ${error.message}
            </div>
        `;
    }
}
