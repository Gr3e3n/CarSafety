# 批量调用区块链网关 POST /upload（读取 generate_blockchain_bulk_data.py 生成的 .jsonl）
# 用法:
#   cd blockchain_test_export\output\20260522_xxxxxx
#   powershell -ExecutionPolicy Bypass -File ..\batch_upload.ps1 -Jsonl blockchain_upload_300.jsonl
#   powershell -ExecutionPolicy Bypass -File ..\batch_upload.ps1 -Jsonl blockchain_upload_300.jsonl -BaseUrl http://192.168.119.128:8080 -Limit 10

param(
    [Parameter(Mandatory = $true)]
    [string]$Jsonl,
    [string]$BaseUrl = "http://192.168.119.128:8080",
    [int]$Limit = 0,
    [int]$DelayMs = 200
)

$uploadUrl = "$BaseUrl/upload"
$lines = Get-Content -Path $Jsonl -Encoding UTF8
$ok = 0
$fail = 0
$i = 0

foreach ($line in $lines) {
    if ([string]::IsNullOrWhiteSpace($line)) { continue }
    $i++
    if ($Limit -gt 0 -and $i -gt $Limit) { break }

    try {
        $resp = Invoke-RestMethod -Uri $uploadUrl -Method Post -Body $line -ContentType "application/json; charset=utf-8"
        if ($resp.success) {
            $ok++
            Write-Host "[$i] OK hash=$($resp.hash)"
        } else {
            $fail++
            Write-Host "[$i] FAIL $($resp | ConvertTo-Json -Compress)"
        }
    } catch {
        $fail++
        Write-Host "[$i] ERROR $($_.Exception.Message)"
    }
    Start-Sleep -Milliseconds $DelayMs
}

Write-Host "Done: ok=$ok fail=$fail total=$i"
