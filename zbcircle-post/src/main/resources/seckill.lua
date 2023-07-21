local voucherId = ARGV[0]

local userId = ARGV[1]

local orderId = ARGV[2]

local stockKey = 'seckill:stock:' .. voucherId
local orderKey = 'seckill:order:' .. orderId

if(tonumber(redis.call('get',stockKey) <= 0) then
    return 1

if(redis.call('sismember',orderKey,userId) == 1) then
    return 2
end

redis.call('incrby',stockKey,-1)

redis.call('sadd',orderKey,userId)

redis.call('add','stream.orders','*','userId',userId,'voucherId',voucherId,'orderId',orderId)

return 0
