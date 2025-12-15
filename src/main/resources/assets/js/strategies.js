
document.addEventListener('DOMContentLoaded', function () {
    renderCompoundingChart();
    renderStepUpChart();
});

function fmtINR(x) {
    const abs = Math.abs(x);
    const sign = x < 0 ? '-' : '';
    if (abs >= 1e7) { return sign + (abs / 1e7).toFixed(2).replace(/\.00$/, '') + ' Cr'; }
    if (abs >= 1e5) { return sign + (abs / 1e5).toFixed(2).replace(/\.00$/, '') + ' L'; }
    return sign + new Intl.NumberFormat('en-IN', { maximumFractionDigits: 0 }).format(abs);
}

function renderCompoundingChart() {
    const ctx = document.getElementById('compoundingChart').getContext('2d');

    // Data for 30 years: Starting 25 vs Starting 35
    // Scenario: Invest 10k/month @ 12%
    // Person A: Starts at 25, invests for 35 years (till 60)
    // Person B: Starts at 35, invests for 25 years (till 60)

    const years = Array.from({ length: 36 }, (_, i) => 25 + i); // Ages 25 to 60
    const corpusA = [];
    const corpusB = [];

    let currentA = 0;
    let currentB = 0;
    const monthlyInv = 10000;
    const rate = 0.12 / 12;

    years.forEach(age => {
        // Calculate for 12 months for this age
        for (let m = 0; m < 12; m++) {
            // Person A always invests
            currentA = (currentA + monthlyInv) * (1 + rate);

            // Person B starts at age 35 (index 10)
            if (age >= 35) {
                currentB = (currentB + monthlyInv) * (1 + rate);
            }
        }
        corpusA.push(currentA);
        corpusB.push(currentB);
    });

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: years,
            datasets: [
                {
                    label: 'Starter A (Starts at 25)',
                    data: corpusA,
                    borderColor: '#2ecc71',
                    backgroundColor: 'rgba(46, 204, 113, 0.1)',
                    fill: true,
                    tension: 0.4
                },
                {
                    label: 'Starter B (Starts at 35)',
                    data: corpusB,
                    borderColor: '#e74c3c',
                    backgroundColor: 'rgba(231, 76, 60, 0.1)',
                    fill: true,
                    tension: 0.4
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Cost of Delay: Investing ₹10k/month @ 12%'
                },
                tooltip: {
                    callbacks: {
                        label: (ctx) => `${ctx.dataset.label}: ₹${fmtINR(ctx.parsed.y)}`
                    }
                }
            },
            scales: {
                y: {
                    ticks: { callback: (val) => '₹' + fmtINR(val) }
                },
                x: {
                    title: { display: true, text: 'Age of Investor' }
                }
            }
        }
    });
}

function renderStepUpChart() {
    const ctx = document.getElementById('stepUpChart').getContext('2d');

    // Scenario: 20 Years, 10k initial
    // Option 1: Normal SIP
    // Option 2: 10% Step Up annually

    const years = Array.from({ length: 21 }, (_, i) => i);
    const normalSIP = [];
    const stepUpSIP = [];

    let balNormal = 0;
    let balStepUp = 0;
    let sipAmount = 10000;
    const rate = 0.12 / 12; // 12% p.a

    years.forEach(year => {
        if (year === 0) {
            normalSIP.push(0);
            stepUpSIP.push(0);
            return;
        }

        // Calculate for this year
        // Step Up increases SIP amount at start of year
        let currentStepSip = sipAmount * Math.pow(1.10, year - 1);

        for (let m = 0; m < 12; m++) {
            balNormal = (balNormal + 10000) * (1 + rate);
            balStepUp = (balStepUp + currentStepSip) * (1 + rate);
        }

        normalSIP.push(balNormal);
        stepUpSIP.push(balStepUp);
    });

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: years,
            datasets: [
                {
                    label: 'Regular SIP',
                    data: normalSIP,
                    backgroundColor: '#3498db'
                },
                {
                    label: '10% Annual Step-Up',
                    data: stepUpSIP,
                    backgroundColor: '#9b59b6'
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Power of Step-Up: 20 Years Growth'
                },
                tooltip: {
                    callbacks: {
                        label: (ctx) => `${ctx.dataset.label}: ₹${fmtINR(ctx.parsed.y)}`
                    }
                }
            },
            scales: {
                y: {
                    ticks: { callback: (val) => '₹' + fmtINR(val) }
                },
                x: {
                    title: { display: true, text: 'Years Invested' }
                }
            }
        }
    });
}
